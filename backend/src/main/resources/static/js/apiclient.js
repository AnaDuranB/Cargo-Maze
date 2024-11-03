/*
await se utiliza para esperar a que la promesa devuelta por fetch
se resuelva, es decir, esperar a que los datos lleguen del servidor.
*/

const apiClient = (() => {

    const url = "http://localhost:8080/cargoMaze/";

    const createPlayer = async (nickname) => {
        let json = JSON.stringify({nickname: nickname });
        let promise = $.ajax({
            url: url + "player",
            type: "POST",
            data: json,
            contentType: "application/json"
        })
        return promise;
    };

    const getGameSession = async (gameSessionId) => {
        try{
            let response = await fetch(`${url}+gameSession/${gameSessionId}`);
            let data = await response.json();
            callback(data);
        }catch(error){
            console.error("Error searching for gameSession by id",error);
        }

    };



    return {
        createPlayer,
        getGameSession
    };

})();