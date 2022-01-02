<?php
    require_once ('connection.php');
    if (!isset($_POST['username'])){
        die(json_encode(array('status' => false, 'message' => 'Parameters not valid')));
    }

    $username = $_POST['username'];
    $sql = 'CREATE TABLE '.$username.' (
                id INT(11) AUTO_INCREMENT PRIMARY KEY,
                user1 VARCHAR(50),
                user2 VARCHAR(50),
                message1 VARCHAR(200),
                message2 VARCHAR(200),
                time VARCHAR(10)
                )';

    try{
        $stmt = $dbCon->prepare($sql);
        $stmt->execute();
        die(json_encode(array('status' => true, 'message' => 'Login success!')));
    }
    catch(PDOException $ex){
        die(json_encode(array('status' => false, 'message' => $ex->getMessage())));
    }
