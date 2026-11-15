// Tier 1 (PostToolUse on Edit|Write): compile or lint what just changed.
// Exit 2 sends stderr back to the agent. Keep this under ~10 s.
// Node runs the same way on macOS, Linux and Windows.
import { execSync } from 'node:child_process'
import { readFileSync } from 'node:fs'

const file = JSON.parse(readFileSync(0, 'utf8')).tool_input?.file_path ?? ''
const root = process.env.CLAUDE_PROJECT_DIR ?? '.'
const mvnw = process.platform === 'win32' ? 'mvnw.cmd' : './mvnw'

let cmd, cwd = root
if (file.endsWith('.java')) cmd = `${mvnw} -q -o -pl backend compile`
else if (/\.(vue|ts)$/.test(file)) { cmd = `pnpm exec eslint "${file}" && pnpm exec vue-tsc --build`; cwd = `${root}/frontend` }
else process.exit(0)

try {
  execSync(cmd, { cwd, stdio: ['ignore', 'pipe', 'pipe'] })
} catch (e) {
  console.error(`${e.stdout ?? ''}${e.stderr ?? ''}`.trim())
  process.exit(2)
}
