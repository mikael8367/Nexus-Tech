# Nexus Tech

Projeto inicial de mod para **Minecraft Java 1.12.2 + Minecraft Forge 14.23.5.2860**, usando Java 8.

## Estado atual

Este commit cria a estrutura compilável do mod e a automação de build/publicação. Ainda não adiciona máquinas, blocos, itens ou receitas de gameplay.

## Compilar

O GitHub Actions executa a compilação automaticamente em cada push para `main` e em pull requests. O artefato JAR fica disponível na aba **Actions**, dentro dos artefatos da execução.

Ambiente local recomendado:
- Java 8
- Gradle 4.10.3
- Minecraft Forge 1.12.2-14.23.5.2860

## Publicar no CurseForge

1. No GitHub, abra **Settings → Secrets and variables → Actions**.
2. Crie o secret `CURSEFORGE_TOKEN` com sua chave de API do CurseForge. Não coloque a chave no código nem a envie pelo chat.
3. Crie a variável `CURSEFORGE_PROJECT_ID` com o ID numérico do projeto Nexus Tech no CurseForge.
4. Abra **Actions → Publish Nexus Tech to CurseForge → Run workflow** e escolha o canal beta, alpha ou release.

A publicação é manual e protegida por credenciais. O workflow não envia arquivos automaticamente em cada push.

## Estrutura

- `src/main/java`: código Java do mod.
- `src/main/resources/mcmod.info`: metadados Forge.
- `.github/workflows/build.yml`: compilação automática.
- `.github/workflows/publish-curseforge.yml`: publicação manual no CurseForge.

## Identificadores

- Mod ID: `nexus_tech`
- Minecraft: `1.12.2`
- Forge: `14.23.5.2860`
- Java: `8`
