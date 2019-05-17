# Using Git for Your Arcade Game Project

For the arcade game project, you will be doing group development.
Therefore, we'll be asking you to use git to manage your code, as 
you've done for your homework and the computer part of exam 2. 
Instead of using your individual repo, you will be using this group
repo that all members of your project project team can contribute to. 

# Step 1: Checking out your team repo

This step should be done by everyone in your team.

1. In your browser, go to https://ada.csse.rose-hulman.edu and verify
   that you have access to your team project repo.  Copy the __https__
   version of the repo URL
2. Open your eclipse and go to __File__ > __Import__ > __Git__ > __Projects from Git__
3. Select __Clone URI__
4. In the URI field paste the URL you copied from https://ada.csse.rose-hulman.edu.  
    __Host__ and __repository path__ should get filled out for you
5. In Authentication, enter your Rose-Hulman credentials (you might find it
   convenient to have it save these for you) and hit Next
6. In branch selection make sure __master__ is checked and click next
7. In local destination, you can configure anywhere you like *__except__*
   the directories that your existing CSSE220 repos are being checked
   out to
8. Select __Import existing eclipse projects__ and select next
9. You should see __AracadeGame__ on the list, make sure it's checked
   and select next
10. You should see a folder for __ArcadeGame__ in your project browser

# Step 2: Test Push and Pull

Have *__one__* member of your team make a change and push it.  

You can [do this using GitBash](https://www.rose-hulman.edu/class/csse/csse220/current/Docs/using_your_class_repo.html#how-to-commit-and-push-your-solutions) OR by following the instructions below:

1.  In eclipse, edit the Main.java file (maybe edit the __@author__?
    annotation, thereby adding your name) and save it
2.  Right click on the project folder and select __Team__ > __Commit__
3.  Verify that Main.java appears in your list of __Staged Changes__
4.  Add some text in the commit message
5.  Select __Commit and push__

Have *__everyone else__* on your team __pull__ the latest version:

Again, you can [do this using GitBash](https://www.rose-hulman.edu/class/csse/csse220/current/Docs/using_your_class_repo.html#how-to-commit-and-push-your-solutions) OR by following the instructions below:

1. Right click on the project folder and select __Team__ > __Pull__
2. You might have to enter your credentials
3. You should get the updated file(s)

# Step 3: Cause a Merge Conflict

Have *__everyone__* in your team

1. Edit the same line of code in a different way.  Say add your name
   to the *println()*.
2. Attempt to commit and push.
3. The first person who does it should succeed.  The rest should get
   a "rejected non-fast-forward" error.

For one of those those who failed: 

1. Right click on the project folder and select __Team__ > __Pull__
2. You should see a message about conflict and things will look sort
   of scary
3. Look at the edited file.  You should see that both versions of the
   code are there plus some <<<<< ===== >>>> lines
4. Figure out what the *__combination__* of the changes ought to be
   (probably all your names in the println) and edit the file to be
   correct, deleting all unnecessary stuff
5. Test your code and make sure that everything works as expected
6. Write click on the project folder and select __Team__ > __Commit__
7. Manually move all your files into __Staged changes__ with the +
8. Commit and push
9. Now have the original committer pull and they should have the
    merged version too
10. If they are any other members of year team, have them do step 4
    onward
11. Here's another resource for managing merge conflicts -- [Resolving a merge conflict using the command line](https://help.github.com/en/articles/resolving-a-merge-conflict-using-the-command-line).

Done!

# Good Advice for Minimal Merge Conflicts

* Pair program whenever possible
* Always do a __Team__ > __Pull__ before you begin programming
* Always to a __Team__ >  __Commit and Push__ when you finish
* If you do have to resolve a merge conflict, remember you must
  accommodate *__both__* changes
