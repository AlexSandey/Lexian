async function cadastrarProduto() {

    var nomeProduto = document.getElementById("nome_produto").value;
    var qtde = document.getElementById("qtde_produto").value;
    var marca = document.getElementById("marca_produto").value;
    var categoria = document.getElementById('categoria_produto').value;
    var descricao = document.getElementById('descricao_produto').value;
    var valor = document.getElementById("valor_produto").value;
   
    //Pegar valor contido no button radio
    var ativo;
    var els = document.getElementsByName('cadastro_ativo');
    for (var i = 0; i < els.length; i++) {
        if (els[i].checked) {
            ativo = (els[i].value);
        }
    }
    console.log(ativo)
    
    const data = {
        'nomeProduto': nomeProduto,
        'qtde': qtde,
        'marca': marca,
        'categoria': categoria,
        'descricao': descricao,
        'valor': valor,
        'ativo': ativo,
    }
    axios({
        method: "POST",
        url: "./produto/cadastrar",
        headers: {
            'Content-Type': 'application/json'
        },
        data: JSON.stringify(data)
    })
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error.response)
            });
}


function listarProdutos() {
    axios({
        method: "GET",
        headers: {  
            "Content-Type": "application/json"
        },
        url: "./produto/lista",
        
        
    })
            .then(
                function (retorno) {
                    console.log(retorno)
                    $.each(retorno.data, function (key, item) {
                        var status = "Desativar"
                        if (item.ativo == true) {
                            status = "Ativar"
                        }
                        if(item.categoria == "placa_de_video"){
                            item.categoria = "Placa de Video"
                        }else if(item.categoria == "processador"){
                            item.categoria = "Processador"
                        }else if(item.categoria == "placa_mae"){
                            item.categoria = "Placa Mãe"
                        }else if(item.categoria == "memoria_ram"){
                            item.categoria = "Memória Ram"
                        }else if(item.categoria == "fonte"){
                            item.categoria = "Fonte"
                        }

                        $("#table tbody").append(
                                "<tr class='row100 body' onclick='modal:open'>" +
                                "<td class='cell100 column1'>" + item.idProduto + "</td>" +
                                "<td class='cell100 column2'>" + item.nomeProduto + "</td>" +
                                "<td class='cell100 column3'>" + item.marca + "</td>" +
                                "<td class='cell100 column4'>" + item.categoria + "</td>" +
                                "<td class='cell100 column5'>" + item.qtde + "</td>" +
                                "<td class='cell100 column6'>" + item.valor + "</td>" +
                                "</tr>")
                    });
                
            })


}

