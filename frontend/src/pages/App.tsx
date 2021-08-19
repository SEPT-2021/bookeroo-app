import React, { useEffect, useState } from "react";

function App() {
  const [message, setMessage] = useState("");

  useEffect(() => {
    fetch("/api/greeting")
      .then((response) => response.text())
      .then((apiMessage) => {
        setMessage(apiMessage);
      });
  }, []);

  return <h1 className="App-title">{message}</h1>;
}

export default App;
