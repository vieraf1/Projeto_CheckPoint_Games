function handleSubmit(xhr, status, args, dialog) {
    var jqDialog = jQuery('#'+dialog.id);
    console.log(args)
    if(args.validationFailed) {
        jqDialog.effect('shake', { times:3 }, 100);
    } else {
        dialog.hide();
    }
}

function handleValidation(xhr,status,args) {
	if(args.validationFailed) {
		
	}
}

function verificaSelecaoDeItem(tabelaSelecao, dialog) {
	if( !isEmpty(tabelaSelecao)) {
		dialog.show();
	}
}

function isEmpty(obj) {
    for(var prop in obj) {
        if(obj.hasOwnProperty(prop))
            return false;
    }
 
    return true;
}

$(document).ready(
		function() {
			$(".maskMoney").maskMoney();
			var menuSelected = $("#menuForm").find(".menuItemSelected");
			$(menuSelected).parents('.ui-panelmenu-header').addClass(
					'ui-state-active')
			$(menuSelected).parents('.ui-panelmenu-content').removeClass(
					'ui-helper-hidden');
			$(menuSelected).parents('.ui-panelmenu-icon').css("background",
					'red');
			$(menuSelected).parents('.ui-menu-list').parent(
					'.ui-panelmenu-icon').css('background', 'red');
			$(menuSelected).parents('.ui-menu-list').css('display', 'block');

			var isVisualizarRegistro = $(".isVisualizarRegistro");

		});

function verificaNovoRegistro() {
	 var isVisualizarRegistro = $(".isVisualizarRegistro");
	 
	 isVisualizarRegistro.find('input, textarea, select').attr('readonly','readonly')

}

function carregaMaskMoney() {
	$(function() {
		$(".maskMoney").maskMoney({symbol:'R$ ', thousands:'.', decimal:',', symbolStay: false, allowNegative:true, defaultZero:true});
	})
}

function start() {  
    PF('startButton1').disable();  

    window['progress'] = setInterval(function() {  
        var pbClient = PF('pbClient'),  
        oldValue = pbClient.getValue(),  
        newValue = oldValue + 10;  

        pbClient.setValue(pbClient.getValue() + 10);  

        if(newValue === 100) {  
            clearInterval(window['progress']);  
        }  


    }, 1000);  
}  

function cancel() {  
    clearInterval(window['progress']);  
    PF('pbClient').setValue(0);  
    PF('startButton1').enable();  
} 

