# Minesweeper

## Introduction
### What is this game ?
This is my version of the famous "Minesweeper" game. 
At the beginning of the game, all the squares are hidden (you don't know what's behind!).
The purpose of the game is to "mark" all the hidden bombs with a red flag. When you're done, that is to say when you have positionned all your flags at the right places, you win the game.
When you click on a square where there is no bomb, the number of neighboring bombs appears on the case. If there are zero neighboring bombs then the number of bombs on the neighboring squares appear as well.
If you click on a square where there is a bomb behind, you loose.

There are three different levels:
- Easy level : The grid is 8x8 and there are 10 hidden bombs.
- Medium level : The grid is 16x16 and there are 40 bombs.
- Difficult level : The grid is 16x32 and there are 99 bombs.

At the end of the game, you get your score. The lowest your score is, the better you are (because the faster you were!=

This is how the game looks like:

![Alt text](Minesweeper.png?raw=true "Minesweeper")

### Code
This is a game developed using Java. The graphic interface was created using the swing library.

There are three different classes, in the src file:
- The Box Class,
- The Field Class,
- The Minesweeper Class, which is the principal class.

The executable file is also provided.

### To-do list:
- Make a "High Score" part
- Make a better design ?

Enjoy !
