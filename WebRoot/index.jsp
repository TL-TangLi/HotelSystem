<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <title>Validation - Ajax</title>
    <!-- Don't forget these 2 files!! -->
    <script  src="./script/utils.js"    type="text/javascript"></script>
    <script  src="./script/validation.js" type="text/javascript"></script>
    
    <!-- Prototype -->
    <script src="./script/prototype.js" type="text/javascript"></script>
    <s:head theme="xhtml" />
</head>

<s:url id="url" namespace="/nodecorate" action="quizAction"/>

<body>

<%-- <s:form method="post" theme="xhtml" namespace="/nodecorate" action="quizAction" id="form" onsubmit="validate(); return false">
    <s:textfield label="Name" name="name" />
    <s:textfield label="Age" name="age" />
    <s:textfield label="Favorite color" name="answer"/>
    <s:submit />
</s:form>
 --%>

<script type="text/javascript">
function validate() {
     var url = '${url}';
     
     new Ajax.Request(
        url, 
        {
            method: 'get', 
            parameters: Form.serialize($('form')) + '&struts.enableJSONValidation=true&struts.validateOnly=true', 
            onComplete: postValidation
        }
     );
}

function postValidation(request) {
     var form = $('form');
     
     //clear previous validation errors, if any
     //StrutsUtils.clearValidationErrors(form);
     
     //get errors from response
     var text = "/* "+ request.responseText+" */";
     var errorsObject = StrutsUtils.getValidationErrors(text);
     // alert("sdfs");
     //show errors, if any
     if(errorsObject.fieldErrors) {
       StrutsUtils.showValidationErrors(form, eval("("+request.responseText+")"));
     } else {
       //good to go, regular submit
       form.submit();
     }
    
}
</script>
</body>

</html>