prerequisites:
  Java 8 , Gradle 4.4, AWS SAM CLI, Docker(local testing).
 

Please follow the steps to build & run the application:

1) Download source code from https://github.com/muralikumare/msgprocessor-lambda.git

2) From inside folder msgprocessor-lambda run command: sam build

A message 'Build Succeeded' should appear if build is successful.

3) To test locally run 'sam local invoke', else skip to next step

4) To package and deploy to aws account, run command: sam deploy --guided

When prompted for 'stack name' and 'aws region', press enter to accept defaults, or enter preferred stackname and region.

Please type 'Y' for subsequent prompts to proceed.

Once stack is successfully deployed, the below message appears on screen.

Successfully created/updated stack - array-combinations in eu-west-2

4) Verify stack creation in AWS console:
 Goto Lambda > Applications > array-combinations(or whatever stackname that was entered).
 All the resources created as part of the stack will be listed here, for our stack this will be:
  - combinator (lambda function).
  - SrcDynamoTable.
  - SrcSqsQueue.

5. Test the application:

 Testing can be done from cmd prompt or from sqs console.
 
 To test from command prompt:
 
     aws sqs send-message --region eu-west-2 --endpoint-url https://sqs.eu-west-2.amazonaws.com/ --queue-url 'url of the sqsqueue' --message-body "{"input":["A","B","C","D"]}"
 
 To test from sqs console:
 
    Goto SrcSqsQueue, click on 'Send and receive messages', enter the json {"input":["A","B","C","D"]}

    check the dynamodb table 'arrayCombinations' via aws console to check the persisted combination records.
    

6) cleanup: 
  Please delete the stack after testing with the command: aws cloudformation delete-stack --stack-name (stack name).
  
 


