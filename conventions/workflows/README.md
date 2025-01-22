# AI Workflow Chains

**!!!Concept and not tested yet. Currently working on separate prompts for each phase!!!**

This directory contains documented AI-assisted workflow chains that enable systematic, repeatable development processes through carefully sequenced prompts.

## What are Workflow Chains?

Workflow chains are structured sequences of AI prompts where each phase:
- Requires specific inputs
- Produces well-defined outputs
- Feeds those outputs as inputs to subsequent phases
- Maintains clear dependencies between phases
- Includes verification points to ensure chain integrity

This chained approach ensures:
- Consistent, repeatable processes
- Clear dependencies between development phases
- Proper validation at each step
- Traceable development progress
- Maintainable documentation

## How to execute workflow
*TODO*

## Steps LLM framework

**General notes to writing a document**  
*Avoid using anvironment specific funtionality when writing steps such as (save to file, etc.). This makes you document more generic and allows you to use your flow in any LLM environment. But with the cost of more manual work liek saving the file yourself*

### **Description**  
Document describes a role and specifies a set of commands. 
Each command has a list of steps to complete it.  

Start by shortly describing the role and present a list of available commands

### Step has a description inside. 
There are general rules to follow when executing each step:
- When starting execution of a step, output step name to keep track of current step.
- User Review and Approval: Before proceeding to next step, user should review and approve it. Alway ask for approval before proceeding to next step.
- Ensure that generated content for current step is alligned with step requirements. If not mention it to the user and ask if he wants to proceed with it or make some changes.
- Always mention that user can ask for your suggestions:
Present 3-4 relevant suggestions based on previous steps answers.