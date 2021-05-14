$(document).ready(function()
	{
	if ($("#alertSuccess").text().trim() == "")
	{
	$("#alertSuccess").hide();
	}
	$("#alertError").hide();
	});
	
// SAVE ============================================
	$(document).on("click", "#btnSave", function(event)
	{
		// Clear alerts---------------------
		$("#alertSuccess").text("");
		$("#alertSuccess").hide();
		$("#alertError").text("");
		$("#alertError").hide();
		
		// Form validation-------------------
	    var status = validatePaymentForm();
		if (status != true)
		{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
		}
		
		 // If valid------------------------
		 var type = ($("#hidpaymentIDSave").val() == "") ? "POST" : "PUT"; 
		 $.ajax( 
		 { 
		 url : "PaymentAPI", 
		 type : type, 
		 data : $("#formPayment").serialize(), 
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 onPaymentSaveComplete(response.responseText, status); 
		 } 
 	}); 
});
		
// UPDATE==========================================
	$(document).on("click", ".btnUpdate", function(event)
	{
	$("#hidpaymentIDSave").val($(this).data("paymentid"));
	$("#customerName").val($(this).closest("tr").find('td:eq(0)').text());
	$("#accountNumber").val($(this).closest("tr").find('td:eq(1)').text());
	$("#productName").val($(this).closest("tr").find('td:eq(2)').text());
	$("#amount").val($(this).closest("tr").find('td:eq(3)').text());
	});
	
// DELETE===========================================
	$(document).on("click", ".btnRemove", function(event)
	{ 
	 $.ajax( 
	 { 
	 url : "PaymentAPI", 
	 type : "DELETE", 
	 data : "paymentID=" + $(this).data("paymentid"),
	 dataType : "text", 
	 complete : function(response, status) 
	 { 
	 onPaymentDeleteComplete(response.responseText, status); 
	 } 
	 }); 
});
// CLIENT-MODEL================================================================
function validatePaymentForm()
	{
	// CustomerName
	if ($("#customerName").val().trim() == "")
	{
	return "Insert Customer Name.";
	}
	// AccountNumber
	if ($("#accountNumber").val().trim() == "")
	{
	return "Insert Account Number.";
	}

	// AMOUNT-------------------------------
	if ($("#amount").val().trim() == "")
	{
	return "Insert Payment Amount.";
	}
	// is numerical value
	var tmpPrice = $("#amount").val().trim();
	if (!$.isNumeric(tmpPrice))
	{
	return "Insert a numerical value for Payment Amount.";
	}
	// convert to decimal price
	$("#amount").val(parseFloat(tmpPrice).toFixed(2));
	
	// PRODUCTNAME------------------------
	if ($("#productName").val().trim() == "")
	{
	return "Insert Product Name.";
	}
	return true;
}

function onPaymentSaveComplete(response, status)
	{ 
	if (status == "success") 
	 { 
	 var resultSet = JSON.parse(response); 
	 if (resultSet.status.trim() == "success") 
	 { 
	 $("#alertSuccess").text("Successfully saved."); 
	 $("#alertSuccess").show();
	 $("#divPaymentsGrid").html(resultSet.data); 
	 } else if (resultSet.status.trim() == "error") 
	 { 
	 $("#alertError").text(resultSet.data); 
	 $("#alertError").show(); 
	 } 
	 } else if (status == "error") 
	 { 
	 $("#alertError").text("Error while saving."); 
	 $("#alertError").show(); 
	 } else
	 { 
	 $("#alertError").text("Unknown error while saving.."); 
	 $("#alertError").show(); 
	 } 
	 $("#hidpaymentIDSave").val(""); 
	 $("#formPayment")[0].reset(); 
}

function onPaymentDeleteComplete(response, status)
	{ 
	if (status == "success") 
	 { 
	 var resultSet = JSON.parse(response); 
	 if (resultSet.status.trim() == "success") 
	 { 
	 $("#alertSuccess").text("Successfully deleted."); 
	 $("#alertSuccess").show();
	 $("#divPaymentsGrid").html(resultSet.data); 
	 } else if (resultSet.status.trim() == "error") 
	 { 
	 $("#alertError").text(resultSet.data); 
	 $("#alertError").show(); 
	 } 
	 } else if (status == "error") 
	 { 
	 $("#alertError").text("Error while deleting."); 
	 $("#alertError").show(); 
	 } else
	 { 
	 $("#alertError").text("Unknown error while deleting.."); 
	 $("#alertError").show(); 
 } 
}


