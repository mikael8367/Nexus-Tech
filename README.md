# Nexus Tech

Minecraft Java 1.12.2 mod built for Forge 14.23.5.2860 and Java 8.

## Languages / Idiomas

The mod includes localized block names and in-game item descriptions (tooltips) in:

- English (primary documentation and localization)
- Português do Brasil (localização prioritária)
- Español
- Русский
- 日本語
- Deutsch
- Français
- Italiano
- 简体中文

English and Brazilian Portuguese receive the most detailed wording and are the primary languages for new content. Other translations are included and should be updated whenever new blocks or items are added.

## Energy system

- Energy Cable: transfers Forge Energy through connected cable networks.
- Solar Panel: generates 8 FE/t in daytime with open sky and no rain.
- Coal Generator: generates 20 FE/t and has a dedicated fuel slot.
- Wind Turbine: generates 4–20 FE/t depending on altitude and weather.
- Energy Cell: stores up to 100,000 FE.

### Cross-mod energy compatibility

Nexus Tech uses Forge Energy (FE) through Forge's `CapabilityEnergy.ENERGY` API. Its generators, cables, energy cell, and powered-machine base expose or consume this standard capability, so compatible machines and storage blocks from other Forge mods can exchange energy without a hard dependency on those mods.

Compatibility includes:
- **Mekanism 1.12.2:** connect through Mekanism Universal Cables or compatible FE sides. Mekanism handles conversion between FE and its internal Joules system.
- **Thermal Expansion / Thermal Foundation:** RF-compatible machines and storage that expose Forge Energy.
- **Ender IO:** machines and capacitors that expose Forge Energy (Micro Infinity).
- **Immersive Engineering:** machines and storage that expose Forge Energy (Immersive Flux).
- **Actually Additions, RFTools, Refined Storage, Applied Energistics 2, and other Forge mods:** where the specific block accepts or exposes Forge Energy.

The exact connection depends on each mod's block, side configuration, and energy settings. Mods that use a separate energy API without a Forge Energy bridge are not automatically compatible; use a supported converter or a compatible cable network for those systems. No third-party mod is a required dependency of Nexus Tech.

All registered block items use localized Vanilla-style tooltips. The standard build workflow creates a GitHub Actions artifact and does not publish automatically to CurseForge.
