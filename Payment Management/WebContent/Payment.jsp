<%@page import= "com.Payment" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Management</title>
<link rel="stylesheet" href="View/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Payment.js"></script>
</head>
<body>

<div class="container"><div class="row"><div class="col-6">

<h1>Payment Management</h1>
<form id="formPayment" name="formPayment" method="post" action="Payment.jsp">
 Customer Name: 
<input id="customerName" name="customerName" type="text" 
 class="form-control form-control-sm">
<br> Account Number: 
<input id="accountNumber" name="accountNumber" type="text" 
 class="form-control form-control-sm">
<br> Product Name: 
<input id="productName" name="productName" type="text" 
 class="form-control form-control-sm">
<br> Total Amount: 
<input id="amount" name="amount" type="text" 
 class="form-control form-control-sm">
<br>
<input id="btnSave" name="btnSave" type="button" value="Save" 
 class="btn btn-primary">
<input type="hidden" id="hidpaymentIDSave" name="hidpaymentIDSave" value="">
</form>

<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>

<%
 out.print(session.getAttribute("statusMsg")); 
%> 
<br>
<div id="divPaymentsGrid">

<%
 Payment paymentObj = new Payment(); 
 out.print(paymentObj.readPayments()); 
%>
</div>

</div></div></div>

</body>
</html>