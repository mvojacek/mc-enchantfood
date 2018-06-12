![build status](https://gitlab.com/mvojacek/mc-enchantfood/badges/master/build.svg)
# EnchantFood 1.11
Enhance most foods in the game with useful
enchantments that increase food value,
saturation, eating speed...  

# CREDIT

#### [Vazkii](http://vazkii.us/)
- [Generic Packet implementation from Psi](src/main/java/com/github/hashtagshell/enchantfood/network/message/MessageGeneric.java)


# DETAILS

[//]: # (%GRADLE%:modid:2:':')

**ModID:** 'enchantfood'  

[//]: # (%GRADLE%:version:2:':')

**Minecraft version:** '1.11'  

[//]: # (%GRADLE%:forgeversion:2:':')

**Forge version:** '13.19.1.2189'  

[//]: # (%GRADLE%:mappings:2:':')

**MCP mappings:** 'snapshot_20170211'  

# DEV ENV SETUP
- Nothing really special, just your good old
  `./gradlew setupDecompWorkspace`
  followed by either
  `./gradlew idea` or `./gradlew eclipse`.
- Once you generate your run configurations
  in your IDE, you have to add some options
  to make the coremod work:  
  **VM Options:**
  `-Dfml.coreMods.load=com.github.hashtagshell.enchantfood.asm.EnchantFoodPlugin`
- Without this this mod will not work properly
  in the development environment.
  It might run, but a lot of features will be
  missing.
- When the mod is packaged into a jar, a
  reference to the coremod is included in
  the META-INF automatically by gradle, so you
  don't have to worry about that.
- You can adjust the version that you are working
  on right now in the *build.properties* file, any
  static options (modid, authors, etc.) is
  in *static.properties*. Private options are
  in *private.properties*, which is not committed
  to git. If the *private.properties* (or any
  other config) file is missing, *missing.properties*
  will be used instead, so compilation etc.
  works without a *private.properties* file.
  The *missing.properties* file defines all the
  options that *private.properties* would, just
  without actual values.
- You can specify the version and build number
  interactively by running `./version`
  (recommended) or `./gradlew specifyVersion`.
- You can build a fully working archive by
  running `./rb` or `./gradlew releaseBuild`.
  This will also bump the build number.
- You can do the same thing as `./rb` but
  without the build number bump with `./rt`
- You can build a full release (that includes
  a deobf, source and javadoc jar) with
  `./release` or `./gradlew release`. Running
  `./release` also runs `./version` to give
  the values a final check, supplying empty
  responses leaves the default values.


# LEGAL

#### Copyright notice:  

<a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-sa/4.0/88x31.png" /></a><br /><span xmlns:dct="http://purl.org/dc/terms/" property="dct:title">EnchantFood</span> by <a xmlns:cc="http://creativecommons.org/ns#" href="https://gitlab.com/mvojacek" property="cc:attributionName" rel="cc:attributionURL">HashtagShell</a> is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/">Creative Commons Attribution-ShareAlike 4.0 International License</a>.<br />Based on a work at <a xmlns:dct="http://purl.org/dc/terms/" href="https://gitlab.com/mvojacek/mc-enchantfood" rel="dct:source">https://gitlab.com/mvojacek/mc-enchantfood</a>.

#### Modpacks:
You are free to re-distribute this mod in a modpack, but only if you have permission to re-distribute all other mods in that modpack.  
