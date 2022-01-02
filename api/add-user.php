<?php
    require_once ('connection.php');

    if (!isset($_POST['username']) || !isset($_POST['password']) || !isset($_POST['phone'])) {
        die(json_encode(array('status' => false, 'message' => 'Parameters not valid')));
    }

    $username = $_POST['username'];
    $password = $_POST['password'];
    $phone = $_POST['phone'];

    $sql = 'INSERT INTO user(username,password,phone) VALUES(?,?,?)';

    try{
        $stmt = $dbCon->prepare($sql);
        $stmt->execute(array($username, $password, $phone));
        
        die(json_encode(array('status' => true, 'data' => $dbCon->lastInsertId(), 'message' => 'Đăng ký thành công')));
    }
    catch(PDOException $ex){
        die(json_encode(array('status' => false, 'message' => $ex->getMessage())));
    }
