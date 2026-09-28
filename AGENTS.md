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
4. **Keep `current-code.md` current.** After code changes land, update
   `current-code.md` (repo root) so it reflects the current state of the source files.
   Include the new commit in the same git commit as the code change.

## Project notes

- Java / Spring Boot expense tracker, Maven, package root `com.panda.expense_tracker_2`.
- See `current-code.md` for the code snapshot.
