


import { setNickname } from './state.js';

const login = (() => {
    var nickname = "";
    var api = apiClient;

    const login = async (newNickname) => {
        setNickname(newNickname);
        try {
            await api.login(nickname);
            console.log("Player created" + nickname);
            localStorage.setItem('nickname', nickname);
            window.location.href = "../templates/sessionMenu.html";
        } catch (error) {
            alert("El nickname ya existe, por favor ingrese otro");
        }
    };

    return {
        login
    };

})();

window.login = login;