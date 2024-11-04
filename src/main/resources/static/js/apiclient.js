/*
await se utiliza para esperar a que la promesa devuelta por fetch
se resuelva, es decir, esperar a que los datos lleguen del servidor.
*/

const apiClient = (() => {

    const url = "http://localhost:8080/cargoMaze/";

    const login = async (nickname) => {
        let json = JSON.stringify({nickname: nickname });
        let promise = $.ajax({
            url: url + "login",
            type: "POST",
            data: json,
            contentType: "application/json"
        })
        return promise;
    };

    const getGameSessionBoard = async (gameSessionId) => {
        let response = await fetch(`${url}session/${gameSessionId}/board/state`);
        let data = await response.json();
        return data;
    };

    const enterSession = async (gameSessionId, nickname) => {
        let json = JSON.stringify({nickname: nickname });
        let promise = $.ajax({
            url: url + "session/" + gameSessionId + "/player",
            type: 'PUT',
            data: json,
            contentType: "application/json"
        })
        return promise; //BUG PROMESA NO SE PROCESA BIEN
    };

    return {
        login,
        getGameSessionBoard,
        enterSession
    };

})();