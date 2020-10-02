async function cadastrarProduto() {

    var nome_produto = document.getElementById("nome_produto").value;
    var qtde_produto = document.getElementById("qtde_produto").value;
    var marca_produto = document.getElementById("marca_produto").value;
    var select = document.getElementById('categoria_produto');
    var categoria_produto = select.options[select.selectedIndex].value;
    var descricao_produto = document.getElementById("descricao_produto").value;
    var valor_produto = document.getElementById("valor_produto").value;
    
    //Imagens Carregadas
    var imagem1m = document.getElementById("img_1_m-convert").value;
    var imagem1g = document.getElementById("img_1_g-convert").value;
    
    var imagem2m = document.getElementById("img_2_m-convert").value;
    var imagem2g = document.getElementById("img_2_g-convert").value;
 
    var imagem3m = document.getElementById("img_3_m-convert").value;
    var imagem3g = document.getElementById("img_3_g-convert").value;    
    
    var imagem4m = document.getElementById("img_4_m-convert").value;
    var imagem4g = document.getElementById("img_4_g-convert").value;
    
    
    var imagem5m = document.getElementById("img_5_m-convert").value;
    var imagem5g = document.getElementById("img_5_g-convert").value;
    
    
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
            "Content-Type": "application/x-www-form-urlencoded"
        },
        url: "/produto",
        params: {
            nomeProduto: nome_produto,
            qtde: qtde_produto,
            marca: marca_produto,
            categoria: categoria_produto,
            descricao: descricao_produto,
            valor: valor_produto,
            ativo: ativo_produto,
            imagem1m: imagem1m
        }
    })
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error.response)
            });
}

async function convertToBase64(local, local_convert) {
   const file = document.querySelector(local).files[0];
   const base64String = await toBase64(file);
   document.getElementById(local_convert).value = base64String
}

const toBase64 = file => new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
});
