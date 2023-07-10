#!/bin/bash
# exec java -jar ./app/build/libs/app-standalone.jar "$@"


#!/bin/bash



# Read the contents of test1.txt from stdin
contents=$(cat)

# Process the contents as needed
# echo "Contents of test1.txt:"
# echo "$contents"
# echo "Contents ended"


# echo "something"
# echo $1
exec java -jar ./app/build/libs/app-standalone.jar "$contents"

