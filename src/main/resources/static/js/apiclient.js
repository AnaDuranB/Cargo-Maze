/*
await se utiliza para esperar a que la promesa devuelta por fetch
se resuelva, es decir, esperar a que los datos lleguen del servidor.
*/

const apiClient = (() => {

    const url = "http://localhost:8080/cargoMaze/";

    const login = async (nickname) => {
        let json = JSON.stringify({nickname: nickname });
        let promise = $.ajax({
            url: url + "players",
            type: "POST",
            data: json,
            contentType: "application/json"
        })
        return promise;
    };

    const getGameSessionBoard = async (gameSessionId) =>  {
        try{
            let response = await fetch(`${url}sessions/${gameSessionId}/board/state`);
            let data = await response.json();
            return data;
        }catch(error){
            console.error("Error searching for gameSession by id",error);
        }
    }


    /*const getGameSession = async (gameSessionId, callback) => {
        try{
            let response = await fetch(`${url}+gameSession/${gameSessionId}`);
            let data = await response.json();
            callback(data);
        }catch(error){
            console.error("Error searching for gameSession by id",error);
        }

    };¨*/

    const enterSession = async (gameSessionId, nickname) => {
        let json = JSON.stringify({ nickname: nickname });

        try {
            let response = await $.ajax({
                url: url + "sessions/" + gameSessionId + "/players",
                type: 'PUT',
                data: json,
                contentType: "application/json"
            });
            console.log(response); // Log successful response
            return response; // Return the response to the caller
        } catch (error) {
            console.error(`Error entering session: ${error.responseText || error.message}`);
            // Optionally handle error UI here
            throw error; // Rethrow the error for further handling if needed
        }
    };

    const getPlayersInSession = async (gameSessionId) => {
        try {
            let response = await fetch(`${url}sessions/${gameSessionId}/players`);
            return await response.json();
        } catch (error) {
            console.error("Error retrieving players in session:", error);
        }
    };

    const movePlayer = async (gameSessionId, nickname, newPosition) => {
        let json = JSON.stringify({"x": newPosition.x, "y": newPosition.y});
        console.log(json);
        try {
            let response = await $.ajax({
                url: url + "sessions/" + gameSessionId + "/players/" + nickname + "/move",
                type: 'PUT',
                data: json,
                contentType: "application/json"
            });
            console.log(response); // Log successful response
            return response; // Return the response to the caller
        } catch (error) {
            console.log(`Error entering session: ${error.responseText || error.message}`);
        }
    };

    return {
        login,
        getGameSessionBoard,
        enterSession,
        getPlayersInSession,
        movePlayer

    };

})();