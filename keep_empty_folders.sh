find -not \( -name .git -prune \) -type d -empty -exec touch {}/.gitkeep \;
