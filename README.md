# Nexus Tech

Projeto base de mod para **Minecraft Java 1.12.2 + Minecraft Forge 14.23.5.2860**, usando Java 8.

## Estado atual

O repositório contém a estrutura inicial de um mod Forge: classe principal, ciclo de vida (preInit/init/postInit), proxy comum/cliente, pontos de registro de blocos e itens, metadados e recursos de idioma. Ainda não adiciona máquinas, blocos, itens ou receitas jogáveis.

## Estrutura Java

- `br.com.mikael.nexustech.NexusTech`: ponto de entrada e ciclo de vida do Forge.
- `util/Reference.java`: identificadores e nomes centrais do mod.
- `proxy/CommonProxy.java`: inicialização compartilhada entre cliente e servidor.
- `proxy/ClientProxy.java`: ponto de inicialização exclusivo do cliente.
- `registry/RegistryHandler.java`: eventos Forge para registrar blocos e itens.

## Recursos

- `mcmod.info`: metadados que o Forge usa para identificar o mod.
- `assets/nexus_tech/lang/en_us.lang`: idioma inglês.
- `assets/nexus_tech/lang/pt_br.lang`: idioma português do Brasil.

Os diretórios de modelos, texturas, blockstates e receitas devem receber arquivos quando os respectivos blocos e itens forem implementados. Não foram adicionados arquivos falsos ou conteúdo de exemplo que apareça no jogo sem estar pronto.

## Compilar

O GitHub Actions compila o projeto automaticamente em cada push para `main` e em pull requests. O JAR fica disponível nos artefatos da execução do workflow.

Ambiente local recomendado:
- Java 8
- Gradle 4.9
- Minecraft Forge 1.12.2-14.23.5.2860

## Publicar no CurseForge

A publicação é manual. No GitHub, abra **Actions → Publish Nexus Tech to CurseForge → Run workflow** e escolha o canal beta, alpha ou release. O workflow de compilação não envia arquivos ao CurseForge.

## Identificadores

- Mod ID: `nexus_tech`
- Minecraft: `1.12.2`
- Forge: `14.23.5.2860`
- Java: `8`
