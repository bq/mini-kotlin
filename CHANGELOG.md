# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
### Added
- Add support for incremental annotation processing.
### Fixed
- Fix sources not getting attached to some packages, now they should be visible from Android Studio.

## [1.2.0] - 2020-02-19
### Added
- Add `sharedActivityViewModel` to Kodein extensions to support shared Activity view models.
### Fixed
- Fix `ConcurrentModificationException`s in store subscriptions' iteration by adding safe iteration over them.
### Changed
- Rename `toggleAbility` to `toggleEnabled` as the name was confusing.
- Upgraded project dependencies.

IMPORTANT: as a result of upgrading dependencies, you may need to target Java 8.

## [1.1.2] - 2020-02-07
### Added
- Add new utilities in `KodeinAndroidUtils` to inject `ViewModelProvider.Factory` instances and retrieve `ViewModel`s with `by viewModel(params)`.

## [1.1.1] - 2020-02-03
### Added
- Add new `View` extensions to change view and view lists visibility status and enabled/disabled status.

## [1.1.0] - 2020-01-28
### Added
- Add `TypedTask` class in order to store simple metadata inside a `Task`.

## [1.0.9] - 2020-01-09
### Added
- Add support for custom backpressure strategies when calling `Store.flowable`.
The default strategy is `BackpressureStrategy.BUFFER`.

### Fixed
- Fix `Store.flowable` not unsubscribing correctly when disposed.

## [1.0.8] - 2019-12-19
### Fixed
- Fix logger not rendering correctly in logcat. Thanks @danielceinos!

## [1.0.7] - 2019-12-04
### Changed
- Make `Resource.empty` an object instead of a class.

## [1.0.6] - 2019-11-20
### Fixed
- Fix `Resource` and `Task` `toString` functions.

## [1.0.5] - 2019-11-20
### Fixed
- Fix `Task` `toString` function.

## [1.0.4] - 2019-11-14
### Added
- Add `equals` and `hashCode` to `Resource` class to ease comparisons.

## [1.0.3] - 2019-11-04
### Fixed
- Fix `getOrNull` not returning a value if said value is set-up in
`Resource.loading`

## [1.0.2] - 2019-10-28
### Added
- `Resource.isTerminal` method to check if a Resource is in a terminal
state (`success` or `failure`)

## [1.0.1] - 2019-10-23
### Added
- `mini-testing` package adds testing utilities for the library.

## [1.0.0] - 2019-10-07
### Added
- Initial architecture release.

[Unreleased]: https://github.com/bq/mini-kotlin/compare/1.2.0...HEAD
[1.2.0]: https://github.com/bq/mini-kotlin/compare/1.1.2...1.2.0
[1.1.2]: https://github.com/bq/mini-kotlin/compare/1.1.1...1.1.2
[1.1.1]: https://github.com/bq/mini-kotlin/compare/1.1.0...1.1.1
[1.1.0]: https://github.com/bq/mini-kotlin/compare/1.0.9...1.1.0
[1.0.9]: https://github.com/bq/mini-kotlin/compare/1.0.8...1.0.9
[1.0.8]: https://github.com/bq/mini-kotlin/compare/1.0.7...1.0.8
[1.0.7]: https://github.com/bq/mini-kotlin/compare/1.0.6...1.0.7
[1.0.6]: https://github.com/bq/mini-kotlin/compare/1.0.5...1.0.6
[1.0.5]: https://github.com/bq/mini-kotlin/compare/1.0.4...1.0.5
[1.0.4]: https://github.com/bq/mini-kotlin/compare/1.0.3...1.0.4
[1.0.3]: https://github.com/bq/mini-kotlin/compare/1.0.2...1.0.3
[1.0.2]: https://github.com/bq/mini-kotlin/compare/1.0.1...1.0.2
[1.0.1]: https://github.com/bq/mini-kotlin/compare/1.0.0...1.0.1
[1.0.0]: https://github.com/bq/mini-kotlin/releases/tag/1.0.0
