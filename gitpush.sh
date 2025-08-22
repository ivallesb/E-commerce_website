#!/bin/bash
<<<<<<< HEAD
=======

# Load credentials from .env file
if [ -f .env ]; then
    source .env
else
    echo "Error: .env file not found!"
    echo "Please create a .env file with GITHUB_USERNAME and GITHUB_TOKEN"
    exit 1
fi
>>>>>>> af3459a (Modified automatic push script)

git add .

echo "Enter your commit message:"
read -r commit_msg

git commit -m "$commit_msg"

git push origin feature/jenkins