
# What's Playing?
DJ performing a live mix? Presenter playing their favourite CD? Or just need a sanity check for your own metadata? **What's Playing** listens to your stream and identifies the music within. Using [Gracenote's](https://developer.gracenote.com/) recognition service, the stream is periodically sampled, fingerprinted, and searched within Gracenote's music database.

# Getting Started

 1. Check you have the required [dependencies](#depenencies).
 2. Grab an API key from [Gracenote](https://developer.gracenote.com/).
 3. Clone this repository ```git clone https://github.com/InsanityRadio/whatsPlaying```.
 4. Download [GNSDK ](https://developer.gracenote.com/gnsdk)
 5. Compile this repository, including the GNSDK J2SE jar files.
 6. Configure the application by editing [config.cfg](https://github.com/InsanityRadio/whatsPlaying/blob/master/config.cfg).
 7. Run the application and watch the metadata come flooding in!

# Dependencies
 - FFmpeg
 - Gracenote GNSDK

# Bugs, Future Development ...
- Feel free to submit issues with suspected bugs, problems, etc.
- Planned and upcoming features are documented in our [Whats Playing roadmap project](https://github.com/InsanityRadio/whatsPlaying/projects/1). 