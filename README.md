# Nexus Tech
Minecraft Java 1.12.2 / Forge 14.23.5.2860 / Java 8.

## Energy system - first stage
- Energy cable: 2,000 FE buffer; routes energy through connected cable networks (up to 64 cable blocks per route search).
- Solar panel: 8 FE/t in daytime, with open sky and no rain.
- Coal generator: dedicated fuel slot for coal/charcoal or coal blocks; 20 FE/t while burning. Pauses when its buffer is full.
- Wind turbine: 4-20 FE/t depending on altitude and weather, with open sky.
- Energy cell: 100,000 FE capacity, accepts and outputs energy.
- TileEntityPoweredMachine is the shared base for future electric machines; it exposes Forge Energy and a safe energy-consumption helper.

The coal generator has a Vanilla-style inventory screen with a dedicated fuel slot. Connect generators to cables and cables to compatible machines or the energy cell. Normal build workflow only creates a GitHub artifact; it does not publish to CurseForge.
