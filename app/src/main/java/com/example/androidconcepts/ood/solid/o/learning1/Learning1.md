Learning 1 demonstrates the usage of open close principle (ocp).
Note :do not apply ocp in cases where we don't know what future changes can be. 
For eg: TextFormat can change (txt, docx etc) but in case of TextExtractor (extracts text from image) we don't what alternates of it can come in future 
(here we are not talking about internal apis of TextExtractor but the TextExtractor as a whole entity).

### cost of abstraction
1) Abstraction make your code more complex. eg : if you see BMW: Car() then immediately thoughts would come in mind that are there any other realizations of Car too?
2) Wrong abstractions can be extremely hard to fix later. 