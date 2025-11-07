# Virtual Pet

## About/Overview

This application simulates a virtual pet that requires care and attention from the user. Similar to classic virtual pets
from the 90s, the pet has various needs (hunger, hygiene, social interaction, and sleep) that must be managed. If these
needs are neglected, the pet will become sad and eventually die. This program creates an interactive experience where
users can develop an emotional connection with their digital companion while learning about responsibility.

## List of Features

- Interactive graphical user interface with visual pet representation
- Four different pet needs to manage: hunger, hygiene, social interaction, and sleep
- Visual indicators showing the current status of each need
- Pet mood system that changes between happy and sad based on need satisfaction
- Warning indicators when needs reach critical levels
- Different pet states: Active, Sleeping, and Dead
- Ability to interact with the pet through four different actions: Feed, Play, Clean, and Sleep
- Time progression system using the Step button
- Visual feedback showing the pet's current mood and state

## How To Run

### Option 1: Run from source

Run `MyPetMain.java` in the `src` folder using your IDE (e.g., IntelliJ).

### Option 2: Run from JAR

Double-click the `pet.jar` file to run it. If it doesn't work, you can run it from the command line:

```bash
java -jar pet.jar
```

Make sure you have Java installed.

### Arguments

No command-line arguments are needed to run the program.

## How to Use the Program

1. **Starting the program**: Once launched, you'll see the pet's interface with status bars for hunger, hygiene, social,
   and sleep needs.

2. **Interacting with your pet**: Use the buttons at the bottom of the window:
    - **Feed**: Decreases hunger and increases hygiene slightly
    - **Play**: Decreases social need but increases hunger and decreases sleep
    - **Clean**: Improves hygiene but may affect other stats slightly
    - **Sleep**: Toggles the pet's sleep state. While sleeping, the pet recovers sleep but gets hungrier and dirtier
    - **Step**: Advances time, causing all needs to change naturally

3. **Monitoring pet status**:
    - Watch the status bars to see how your pet is doing
    - When a need becomes critical, the number will turn red and a warning icon will appear
    - The pet's mood will change from happy to sad if multiple needs are neglected
    - If any need reaches its maximum critical value, your pet will die

4. **Pet states**:
    - **Active**: Normal state where the pet can interact
    - **Sleeping**: The pet is asleep and can only be woken up (can't feed, play, or clean)
    - **Dead**: If a need reaches a critical threshold, the pet dies and can no longer interact

## Design/Model Changes

### Version 1.0 to 2.0:

- **Pet State System**: Added separate states (Active, Sleeping, Dead) to better track what the pet is doing
- **Mood Strategy Pattern**: Created a flexible way to change pet behavior based on mood
- **Configurable Needs**: Made hunger, hygiene, sleep, and social limits adjustable
- **Enhanced Sleep**: Made the sleeping mechanism more realistic and interactive
- **Improved Pet Death**: Added better tracking of when a pet should die from neglect
- **Neglect Tracking**: Added a system to track how long the pet has been neglected

### Version 2.0 to 3.0:

- **Warning System**: Added visual alerts (red text and warning icons) when pet needs attention
- **Better UI Panels**: Improved the display of pet needs with custom status panels
- **Reliable Images**: Fixed image loading to work both during development and when packaged as JAR

These changes made the game more user-friendly with better visual feedback, more consistent across different
environments, and easier to maintain and update in the future.

## Assumptions

1. The pet's needs change at a fixed rate that provides a balanced gameplay experience
2. Users understand the basic concept of a virtual pet and how to interact with it
3. The default configuration values for need limits are appropriate for engaging gameplay

## Limitations

1. The application doesn't save the pet's state between sessions
2. There is no customization for the pet's appearance or name
3. There are no sound effects or animations for interactions

## Citations

No external resources were used that require citation. The design was inspired by classic virtual pet toys and games,
but no specific code or assets were copied from external sources.