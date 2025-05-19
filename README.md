# Project Maintenance

## Updating to a newer Minecraft version
 * Change `io.papermc.paper:paper-api`'s version in `build.gradle`
 * ```bash
   wget https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
   ```
 * Build desired bukkit version by running ```java -jar BuildTools.jar --rev <VERSION>```
 * From `spigot-_.__._.jar` extract and move to `$PROJECS/.idea/libraries/` following files:
   * `META-INF/versions/spigot-_.__._-R0.1-SNAPSHOT.jar`
   * `META-INF/libraries/authlib-_._.__.jar`
 * Update respective names in `build.gradle`
 * Go through the code and update errors due to version changes.

## Building plugin `.jar`
### IntellijIdea
 * Go to `Run -> Edit Configurations...`
 * In `Run` add parameter `:jar`
 * Run the project
 * `$PROJECT/build/libs/` should contain a jar file of the plugin

### Terminal
```bash
./gradlew build :jar
```