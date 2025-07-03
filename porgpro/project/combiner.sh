#!/bin/bash

# Output file
OUTPUT_FILE="Combined.java"
MAIN_FILE="./main.java"

# Temp files
IMPORTS_FILE=$(mktemp)
BODY_FILE=$(mktemp)

# Clear output
> "$OUTPUT_FILE"
> "$IMPORTS_FILE"
> "$BODY_FILE"

# Find all .java files and process them
find . -name "*.java" ! -name "main.java" | while read -r file; do
  # Extract imports, skip package declarations, keep the rest
  grep '^import ' "$file" | grep -v '^import project\.' >> "$IMPORTS_FILE"
  sed '/^package /d; /^import /d; s/^public //' "$file" >> "$BODY_FILE"
  echo -e "\n" >> "$BODY_FILE"
done

# Deduplicate and sort imports
sort -u "$IMPORTS_FILE" >> "$OUTPUT_FILE"
echo -e "\n" >> "$OUTPUT_FILE"

# Add main method
cat "$MAIN_FILE" >> "$OUTPUT_FILE"

# Add the combined body
cat "$BODY_FILE" >> "$OUTPUT_FILE"

# Clean up temp files
rm "$IMPORTS_FILE" "$BODY_FILE"
