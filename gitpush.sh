#!/bin/bash
git add .
echo "Enter your commit message:"
read -r commit_msg
git commit -m "$commit_msg"
git push origin feature/jenkins
