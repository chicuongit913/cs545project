$(document).ready(function() {
	
	$('.order-btn').click(function(event){

		event.preventDefault();
		let productId = $(this).data("id");
		let productName = $(this).data("name");
		$.ajax({
			url: '/buyer/rest/cart/add/' + productId,
			type: 'PUT',
			dataType: "json",
			success: function(response){
				swal({
					icon: "success",
					title: "Great!",
					text: productName + " is added to your cart.",
					buttons: {
						cancel: "Continue shopping",
						checkout: "Checkout"
					}
				}).then((value) => {
					switch (value) {
						case "checkout":
							window.location.href = "/buyer/cart";
							break;
					}
				});
			},
			error: function(){
				swal("something went wrong!","error");
			}
		});
	});
	
	$('.product-remove-btn').click(function(event){
		event.preventDefault();
		var productId = $(this).data("id");
		
		$.ajax({
			url: '/buyer/rest/cart/remove/'+ productId,
			type: 'PUT',
			dataType: "json",
			success: function (response) {
				location.reload(true);
			},
			error: function(){						
				alert('Error while request..');
			} 
		});
	});
});

 