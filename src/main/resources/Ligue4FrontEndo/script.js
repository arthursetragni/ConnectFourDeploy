// Exemplo de como adicionar uma classe à primeira célula do grid
var board = document.getElementById('board');
for (var i = 0; i < 6; i++) {
  for (var j = 0; j < 7; j++) {
    var cell = document.createElement('div');
    cell.classList.add('grid-cell');

    // Adiciona a classe 'first-cell' à primeira célula do grid
    if (j === 0 && i === 0) {
      cell.classList.add('first-cell');
    }

    board.appendChild(cell);
  }
}


// document.addEventListener('DOMContentLoaded', function () {
//     $.ajax({
//         url: 'http://localhost:6798/tabuleiro/1',
//         dataType: 'json', // Defina o tipo de dados esperado
//         headers: {
//           'Accept': 'application/json'
//       },
//         success: function (data) {
//             // O parâmetro "data" já é uma matriz de inteiros
//             var tabuleiro = data;

//             // Construa o HTML do tabuleiro com base nos valores
//             var htmlTabuleiro = '';
//             for (var i = 0; i < tabuleiro.length; i++) {
//                 for (var j = 0; j < tabuleiro[i].length; j++) {
//                     var cellValue = tabuleiro[i][j];
//                     var cellClass = '';

//                     // Adiciona classes com base no valor da matriz
//                     switch (cellValue) {
//                         case 0:
//                             cellClass = 'celulaNeutra';
//                             break;
//                         case 1:
//                             cellClass = 'celulaAzul';
//                             break;
//                         case 2:
//                             cellClass = 'celulaVermelha';
//                             break;
//                         default:
//                             // Lida com valores desconhecidos, se necessário
//                             break;
//                     }

//                     // Adiciona a célula ao HTML do tabuleiro
//                     htmlTabuleiro += '<div class="grid-cell ' + cellClass + '">' + '</div>';
//                 }
//                 // Adiciona uma quebra de linha para cada nova linha no tabuleiro
//             }

//             // Preenche a div do tabuleiro com o HTML gerado
//             $('#board').html(htmlTabuleiro);
//         },
//         error: function (error) {
//             console.error('Erro ao obter dados do servidor:', error);
//         }
//     });
// });
document.addEventListener('DOMContentLoaded', function () {
    fetch('http://localhost:6798/tabuleiro/1', {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro ao obter dados do servidor');
        }
        return response.json();
    })
    .then(data => {
        var tabuleiro = data;

        var htmlTabuleiro = '';
        for (var i = 0; i < tabuleiro.length; i++) {
            for (var j = 0; j < tabuleiro[i].length; j++) {
                var cellValue = tabuleiro[i][j];
                var cellClass = '';

                switch (cellValue) {
                    case 0:
                        cellClass = 'celulaNeutra';
                        break;
                    case 1:
                        cellClass = 'celulaAzul';
                        break;
                    case 2:
                        cellClass = 'celulaVermelha';
                        break;
                    default:
                        // Lida com valores desconhecidos, se necessário
                        break;
                }

                htmlTabuleiro += '<div class="grid-cell ' + cellClass + '">' + '</div>';
            }
        }

        $('#board').html(htmlTabuleiro);
    })
    .catch(error => {
        console.error('Erro ao obter dados do servidor:', error.message);
    });
});

