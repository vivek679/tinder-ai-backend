# Tinder AI App  

### Overview

1. **PreProcessing**
   * **Profiles**
     * **_RandomProfile Generation using OLLAMA_**
       - Some random profile should exist so that is could be loaded on UI.
       - Get profile based on gender preferences `By Gender | MALE , FEMALE , NON_BINARY `
       - Register new user. 
         - From properties file
         - REST endpoint to register new user

2. **Swipes**
   * **Get random profiles**
     * Either `Like` or `Dislike`
       * Dislike 
         - Load new random profile
       * Like
         - Notify the `liked profile` that it has been `liked`
         - Check if `match` happened 
           - `Yes` 
             - Create an entry `match`.
           - `No`
             - Nothing to do
             - Check for future use case.

3. **Matches**
    * ** Get All profiles matched for that profile **
      * For particular match
        * check if conversation exist ?
          * NO
            - Create new conversations `entry in conversation table`
            - Start the conversation 
          * YES
            - Load conversations based on timestamp
            - Start the conversation 
        - A
