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
6. **Read project files individually.** When asked to read the project, go through each
   file one by one with the Read tool rather than relying on glob/grep summaries.
   Never use `current-code.md` as a source of truth — it can be stale. Always read the
   actual source files on disk.

## Issue tracker

Issues live in GitHub (`gh issue list`). Keep this section in sync.

### Closed issues

- #1 Rewrite Income to match the design
- #6 LogbookController and list endpoint
- #5 Require income at logbook creation
- #3 Expense entity and repository
- #4 Logbook relationships and cascade delete
- #2 Decision: BigDecimal money type for Income and Expense
- #7 Expense service, DTOs, and controller
- #8 Summary endpoint and math
- #9 Decision: open design questions
- #10 Validation: starter dependency and constraints
- #11 Consistent error handling
- #12 DTO and entity style consistency
- #14 V2-01 Fix summary when a logbook has no income
- #15 V2-02 Close validation gaps
- #16 V2-03 Transactions and global exception handlers
- #17 V2-04 User entity and repository
- #18 V2-05 Registration endpoint

### Skipped issues

- #13 Tests (skipped by request, still open on GitHub)

### Current issue

- #19

## Project notes

- Java / Spring Boot expense tracker, Maven, package root `com.panda.expense_tracker_2`.
- See `current-code.md` for the code snapshot.
- When you need to read my code, read it manually. Do not use grep, glob, or other automated tools to summarize files. Use the Read tool to open each file individually.
- Run the build with `.\mvnw.cmd test`; `mvn` is not on PATH.

## Service conventions

- Every service method is `@Transactional`; read paths use `@Transactional(readOnly = true)`.
- Money is `BigDecimal` (precision 12, scale 2). Failures are `NotFoundException` (404),
  `BadRequestException` (400), or `ConflictException` (409), all rendered as `ErrorResponse`.
- `AuthService.register`: normalize email with `trim().toLowerCase(Locale.ROOT)`, reject duplicates
  with `ConflictException("email already exists")`, hash via `PasswordEncoder`, and translate
  `DataIntegrityViolationException` from `save` into that same 409 conflict.
- `LogbookService.createLogbook` requires both income amount and date. `deleteLogbook` requires the
  logbook but treats a missing income as `null` (`orElse(null)`), and income/expenses are removed by
  JPA cascade.
- `ExpenseService` checks the category is active before logging an expense; `getExpenses` keeps the
  logbook existence check but does not assign the lookup result to a variable.
- `CategoryService.createCategory` trims the name, reactivates a soft-deleted category, and conflicts
  on an active duplicate; deletion is soft (`active = false`).
- `SummaryService` checks logbook existence, sums expenses with `COALESCE`, requires income, and
  returns `income - totalExpenditure`.

## Test conventions

- Service unit tests use JUnit 5 + `MockitoExtension` with `@Mock` and `@InjectMocks`; verify the
  captured arguments, not just the return value (see `AuthServiceTest`, `SummaryServiceTest`).
- Controller tests use `@WebMvcTest` + `@MockitoBean` + `MockMvc`. Security filters are active in
  the slice, so use `@WithMockUser` and `.with(csrf())` on POSTs. Do not reintroduce
  `@AutoConfigureMockMvc(addFilters = false)`.
- Repository constraints are covered with `@DataJpaTest` (`ddl-auto=create-drop`).
- DTO validation without a controller is tested directly with
  `Validation.buildDefaultValidatorFactory()` (see `RequestValidationTest`).
- Assert the `ErrorResponse` shape (`message`, numeric `status`, `timestamp`) and keep the exact
  messages already used: `Malformed JSON`, `Method not allowed`, `No endpoint found`,
  `An unexpected error occurred`, `Invalid value '...' for parameter '...'`, and
  `[field: message]` for bean validation.
- Name tests as action + outcome (e.g. `registerReturns201OnSuccess`), not `SuccessThrows201`.
- Keep all 21 tests passing, and sync `current-code.md` in the same commit as any source change.

## Design constraints from the issue tracker

- Every Logbook and Category has an owner; users only see their own data.
- Another user's record returns 404, never 403.
- All errors use the existing `ErrorResponse` shape.
