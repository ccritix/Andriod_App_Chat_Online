<?php
    require_once ('connection.php');

    if (!isset($_POST['username']) || !isset($_POST['password'])){
        die(json_encode(array('status' => false, 'message' => 'Parameters not valid')));
    }

    $username = $_POST['username'];
    $password = $_POST['password'];
    $sql = 'SELECT * FROM user where username = ? and password = ?';

    try{
        $stmt = $dbCon->prepare($sql);
        $stmt->execute(array($username, $password));

        $count = $stmt->rowCount();

        if ($count == 1){
            $row = $stmt->fetch(PDO::FETCH_ASSOC);
            $data = $row['phone'];
            die(json_encode(array('status' => true, 'data' => $data, 'message' => 'Login success!')));
        }else {
            die(json_encode(array('status' => false, 'message' => 'Invalid Login!')));
        }
    }
    catch(PDOException $ex){
        die(json_encode(array('status' => false, 'message' => $ex->getMessage())));
    }