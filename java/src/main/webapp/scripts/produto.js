function cadastrarProduto() {
    var nome_produto = document.getElementById("nome_produto").value;
    var qtde_produto = document.getElementById("qtde_produto").value;
    var marca_produto = document.getElementById("marca_produto").value;
    var select = document.getElementById('categoria_produto');
    var categoria_produto = select.options[select.selectedIndex].value;
    var descricao_produto = document.getElementById("descricao_produto").value;
    var valor_produto = document.getElementById("valor_produto").value;
    var faq_produto = document.getElementById("faq_produto").value;
    
    //Pegar valor contido no button radio
    var choices = [];
    var els = document.getElementsByName('name');
    for (var i = 0; i < els.length; i++) {
        if (els[i].checked) {
            choices.push(els[i].value);
        }
    }
    console.log(els)
    var ativo_produto = els

    axios({
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        url: "/produto",
        params: {
            nomeProduto: nome_produto,
            qtde: qtde_produto,
            marca: marca_produto,
            categoria: categoria_produto,
            descricao: descricao_produto,
            valor: valor_produto,
            faq: faq_produto,
            ativo: ativo_produto

        }
    })
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error.response)
            });
}

