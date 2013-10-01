[when] Find a customer = $customer : Customer()
[when] - contained {discount} = subscriptions contains {discount}
[when] Find customer's invoice = $invoice : Invoice( customer == $customer )
[when] Find customer's billing record = $record : BillingRecord( customer == $customer )
[when] Find customer's SMS billing record = $record : SMSBillingRecord( customer == $customer )
[when] Proceed if the record is billable = not NonBillable( record == $record )
[when] Calculate total of customer's SMS billing records = accumulate( SMSBillingRecord( $price : price, customer == $customer ); $total : sum( $price ) )
[when] Proceed if all SMS billing records with higher price are already non-billable = not( $top : SMSBillingRecord( customer == $customer, price > $record.price ) and not NonBillable( record == $top ) )
[when] Proceed if there are no other non-billable records for the customer = not( NonBillable( record.customer == $customer, record != $record ) )

[then] Create an invoice = insert( new Invoice($customer) );
[then] Create a fixed billing record of {amount} = insert( new FixedBillingRecord($customer, {amount}) )
[then] Create a fixed discount billing record of {percent} percent = insertLogical( new FixedBillingRecord($customer, $total * (-{percent}) / 100) );
[then] Mark the billing record as non-billable = insertLogical( new NonBillable($record) );
[then] Add the record to the invoice = $invoice.addRecord($record);
[then] Add the amount to the invoice total = $invoice.add($price);
