


// Name can't be blank
//$('#name').on('input', function() {
//	var input=$(this);
//	var is_name=input.val();
//	if(is_name){input.removeClass("invalid").addClass("valid");}
//	else{input.removeClass("valid").addClass("invalid");}
//});


var $form = $("form"),
  $successMsg = $(".alert");
$.validator.addMethod("letters", function(value, element) {
  return this.optional(element) || value == value.match(/^[a-zA-Z\s]*$/);
});
$form.validate({
  rules: {
    name: {
      required: true,
      minlength: 3,
      letters: true,
      color :  red
    },
    introdueced, discontinued: {
      required: true,
      date: true
    }
  },
  messages: {
    name: "Please specify your name (only letters and spaces are allowed)",
    date: "Please specify a valid date"
  },
  submitHandler: function() {
    $successMsg.show();
  }
});