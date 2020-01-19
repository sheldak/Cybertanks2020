# Cybertanks 2020

## How to run
clone repository and write `mvn clean javafx:run` in root directory of the project

## How to play
- click left mouse button to select a tank
- click right mouse button to move selected tank; it costs 1 action point to move to one of the four neighouring fields
- click left mouse button to set a target for selected tank (it has to be on the same x or y coordinate); after your opponent's move there will be an explosion destroying tanks and destructible walls which will be located in area 3x3 with center marked by the target; it costs 3 action points
- you have to use all action points

## Types of fields
- green: plain - you can shoot over it and move to it
- blue: river - you can shoot over it but you can't move to it
- light grey: solid wall - you can neither shoot over it nor move to it
- dark grey: destructible wall - you can destroy it by putting a target near the wall
