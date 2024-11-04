let nickname = "";

const setNickname = (newNickname) => {
    nickname = newNickname;
};

const getNickname = () => {
    return nickname;
};

export { setNickname, getNickname };