import { globalIgnores } from 'eslint/config'
import { defineConfigWithVueTs, vueTsConfigs } from '@vue/eslint-config-typescript'
import pluginVue from 'eslint-plugin-vue'
import pluginCypress from 'eslint-plugin-cypress'
import pluginVitest from '@vitest/eslint-plugin'

export default defineConfigWithVueTs(
  { name: 'app/files-to-lint', files: ['**/*.{vue,ts,mts,tsx}'] },
  globalIgnores(['**/dist/**', '**/coverage/**']),
  ...pluginVue.configs['flat/recommended'],
  vueTsConfigs.recommended,
  { ...pluginCypress.configs.recommended, files: ['cypress/**/*.ts'] },
  { ...pluginVitest.configs.recommended, files: ['src/**/__tests__/*'] },
)
