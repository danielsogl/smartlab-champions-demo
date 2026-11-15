// Tier 2 (Stop): no "done" while tests or lint are red.
// Exception: a test case that is not committed yet may fail on an assertion. That is RED,
// waiting for the human review. Committed test cases are reviewed and must stay green.
// Exit 2 blocks the stop; stderr is the reason the agent reads.
// Node runs the same way on macOS, Linux and Windows.
import { execSync } from 'node:child_process'
import { existsSync, readFileSync, readdirSync, rmSync } from 'node:fs'
import { join, relative } from 'node:path'

const run = (cmd) => execSync(cmd, { encoding: 'utf8', stdio: ['ignore', 'pipe', 'pipe'] })
const mvnw = process.platform === 'win32' ? 'mvnw.cmd' : './mvnw'
const block = (cmd, e) => {
  const out = `${e.stdout ?? ''}${e.stderr ?? ''}`.trim().split('\n').slice(-40).join('\n')
  console.error(`Checks failed: ${cmd}\nFix them before you finish. Do not edit committed tests.\n${out}`)
  process.exit(2)
}

let uncommitted
try {
  process.chdir(process.env.CLAUDE_PROJECT_DIR ?? '.')
  uncommitted = run('git status --porcelain -uall -- backend frontend').split('\n').filter(Boolean).map((l) => l.slice(3))
} catch (e) {
  console.error(`verify hook could not start: ${e.message}`)
  process.exit(2)
}
// No source change, nothing to check (answers, reviews, review.md files)
if (uncommitted.length === 0) process.exit(0)

// A test case is new if its file is uncommitted and the last commit does not contain it yet.
const committed = (file) => { try { return run(`git show HEAD:${file}`) } catch { return '' } }
const isNew = (file, marker) => uncommitted.includes(file) && !committed(file).includes(marker)

try { run('pnpm -C frontend lint') } catch (e) { block('pnpm -C frontend lint', e) }

// Backend: failures in new test methods may stay red. An error (exception, no context) is a broken test.
const reports = 'backend/target/surefire-reports'
rmSync(reports, { recursive: true, force: true })
try {
  run(`${mvnw} -q -pl backend test`)
} catch (e) {
  const cases = existsSync(reports)
    ? readdirSync(reports).filter((f) => /^TEST-.*\.xml$/.test(f))
      .flatMap((f) => [...readFileSync(join(reports, f), 'utf8').matchAll(/<testcase\b([^>]*?)(?:\/>|>([\s\S]*?)<\/testcase>)/g)])
      .filter(([, , body = '']) => /<(failure|error)\b/.test(body))
    : []
  const onlyNewRed = cases.length > 0 && cases.every(([, attrs, body]) => {
    const name = attrs.match(/\bname="([^"(\[]+)/)?.[1]
    const file = `backend/src/test/java/${attrs.match(/\bclassname="([^"]+)"/)?.[1].replaceAll('.', '/')}.java`
    return !/<error\b/.test(body) && isNew(file, ` ${name}(`)
  })
  if (!onlyNewRed) block(`${mvnw} -q -pl backend test`, e)
}

// Frontend: same rule. A file that fails without a failed assertion did not load: broken.
const json = 'frontend/node_modules/.vitest-result.json'
rmSync(json, { force: true })
try {
  run('pnpm -C frontend test:unit --reporter=default --reporter=json --outputFile.json=node_modules/.vitest-result.json')
} catch (e) {
  const files = existsSync(json) ? JSON.parse(readFileSync(json, 'utf8')).testResults : []
  const failing = files.filter((f) => f.status !== 'passed')
  const onlyNewRed = failing.length > 0 && failing.every((f) => {
    const file = relative('.', f.name).replaceAll('\\', '/')
    const red = f.assertionResults.filter((a) => a.status === 'failed')
    return red.length > 0 && red.every((a) => isNew(file, a.title))
  })
  if (!onlyNewRed) block('pnpm -C frontend test:unit', e)
}
