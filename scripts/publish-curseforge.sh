#!/usr/bin/env bash
set -Eeuo pipefail

: "${CURSEFORGE_TOKEN:?Missing CURSEFORGE_TOKEN secret}"
: "${CURSEFORGE_PROJECT_ID:?Missing CURSEFORGE_PROJECT_ID variable}"

JAR_PATH="$(find build/libs -maxdepth 1 -type f -name '*.jar' ! -name '*sources*' -print -quit)"
if [[ -z "${JAR_PATH}" || ! -f "${JAR_PATH}" ]]; then
  echo "No built mod JAR found in build/libs."
  exit 1
fi

RELEASE_TYPE="${RELEASE_TYPE:-beta}"
case "${RELEASE_TYPE}" in
  alpha|beta|release) ;;
  *)
    echo "Invalid RELEASE_TYPE: ${RELEASE_TYPE}"
    exit 1
    ;;
esac

MOD_VERSION="$(sed -n 's/^mod_version=//p' gradle.properties | head -n 1)"
MC_VERSION="$(sed -n 's/^minecraft_version=//p' gradle.properties | head -n 1)"
MOD_VERSION="${MOD_VERSION:-1.0.0}"
MC_VERSION="${MC_VERSION:-1.12.2}"

# CurseForge requires an Environment tag for Minecraft mods.
# This project is a common Forge mod and supports both client and server.
METADATA="$(jq -n \
  --arg releaseType "${RELEASE_TYPE}" \
  --arg displayName "Nexus Tech - ${MOD_VERSION} (Minecraft ${MC_VERSION})" \
  --arg mcVersion "${MC_VERSION}" \
  '{
    changelog: "Nexus Tech para Minecraft 1.12.2 Forge.",
    changelogType: "markdown",
    displayName: $displayName,
    releaseType: $releaseType,
    gameVersionNames: [$mcVersion, "Forge", "Client", "Server", "Java 8"]
  }'
)"

echo "Uploading $(basename "${JAR_PATH}") to CurseForge..."
RESPONSE="$(curl --fail-with-body --silent --show-error \
  --request POST \
  --header "X-Api-Token: ${CURSEFORGE_TOKEN}" \
  --form-string "metadata=${METADATA}" \
  --form "file=@${JAR_PATH};type=application/java-archive" \
  "https://minecraft.curseforge.com/api/projects/${CURSEFORGE_PROJECT_ID}/upload-file")"

FILE_ID="$(printf '%s' "${RESPONSE}" | jq -er '.id')"
echo "CurseForge accepted the upload. File ID: ${FILE_ID}"
