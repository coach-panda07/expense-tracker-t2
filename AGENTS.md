# Project Rules

## Working rules for this project

1. **Questions only -> no changes.** When the user asks a question, answer it and make no
   file edits, no refactors, no formatting, no "helpful" side changes.
2. **No code unless requested.** Do not output code blocks, patches, or code suggestions
   unless the user explicitly asks for code.
3. **Auto-commit after a few changes.** After a small number of related changes are
   verified (a few edits, not one per keystroke), create a git commit on its own. Match
   the existing commit message style: short imperative sentence, e.g.
   `Fix deleteLogbook return type and sync current-code.md with Logbook files`.
   Never commit secrets, never force-push, never amend unless asked.
3a. **Always push after committing.** Follow every commit with `git push` so
   `origin/main` never falls behind local `main`. Fast-forward only, never force.
4. **Keep `current-code.md` current.** After code changes land, update
   `current-code.md` (repo root) so it reflects the current state of the source files.
   Include the new commit in the same git commit as the code change.
5. **Track issue progress here.** Work the GitHub issues in order. When an issue is
   closed (`gh issue close <n>`), note it under "Closed issues" below and set the next
   open issue under "Current issue". Never leave a closed issue unrecorded.

## Issue tracker

Issues live in GitHub (`gh issue list`). Keep this section in sync.

### Closed issues

- #1 Rewrite Income to match the design
- #6 LogbookController and list endpoint

### Current issue

- #5 Require income at logbook creation (in progress)

### Next issue

- #3 Expense entity and repository (then #4 Logbook relationships and cascade delete)

### Remaining, in build order

- #2 Decision: BigDecimal money type for Income and Expense
- #7 Expense service, DTOs, and controller
- #8 Summary endpoint and math
- #9 Decision: open design questions
- #10 Validation: starter dependency and constraints
- #11 Consistent error handling
- #12 DTO and entity style consistency
- #13 Tests

## Project notes

- Java / Spring Boot expense tracker, Maven, package root `com.panda.expense_tracker_2`.
- See `current-code.md` for the code snapshot.
