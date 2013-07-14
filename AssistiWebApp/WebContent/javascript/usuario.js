$(function() {
	var server = "http://localhost" + ":8080/AssistiWebApp/servico/usuario";
	
	function cadastrar() {
		$.ajax({
			type:'POST',
			contentType: 'application/json',
			url: server +"",
			data: formUsuarioToJson(),
			success: function(data, textStatus, jqXHR){
				if(data===true){
					alert('Usuario cadastrado');
				}
				if(data===false){
					alert('Usuario existente');
				}
			},
			error: function(jqXHR, textStatus, errorThrown){
                //$.mobile.dialog.prototype.options.closeBtnText;
                alert('Erro ao cadastrar: ' + textStatus);
			}
		});
//		$.post(server, formUsuarioToJson()).fail(function(){ alert("Erro ao cadastrar");});
	}
	
	function formUsuarioToJson(){
		return JSON.stringify({
			"nome": $('#nomeUsuario').val(),
			"senha": $('#senhaUsuario').val(),
			"email": $('#emailUsuario').val(),
			"login": $('#loginUsuario').val(),
		});
	}
	
	function addUsuarioAoHTML(id, nome) {

		var $usuario = "<li id="+id+"><a href='#'>"+nome+ "</a></li>";
					
			$("#listaDeUsuarios").append($usuario);
			$("#listaDeUsuarios").listview( "refresh" );
	}

	function carregarUsuarios() {
		$.getJSON(server + "").done(
				function(data) {
					console.log("data: ", data);
					$("#listaDeUsuarios").empty();
					for ( var usuario = 0; usuario < data.length; usuario++) {
						console.log("ENTROU NO FOR", usuario);
						addUsuarioAoHTML( data[usuario].id,
								data[usuario].nome);
					}
				});
	}
	
	$("#listarUsuarios").click(carregarUsuarios);
	$("#cadastrarUsuario").click(cadastrar);
});