<?php
    require_once('connection.php');

    if (!isset($_POST['username1']) || !isset($_POST['username2']) || !isset($_POST['message1']) || !isset($_POST['message2']) || !isset($_POST['time'])) {
        die(json_encode(array('status' => false, 'message' => 'Parameters not valid')));
    }

    $username1 = $_POST['username1'];
    $username2 = $_POST['username2'];
    $message1 = $_POST['message1'];
    $message2 = $_POST['message2'];
    $time = $_POST['time'];

    $sql = 'INSERT INTO '.$username1.'(user1,user2,message1, message2, time) VALUES(?,?,?,?,?)';

    try {
        $stmt = $dbCon->prepare($sql);
        $stmt->execute(array($username1, $username2, $message1, $message2, $time));

        die(json_encode(array('status' => true, 'data' => $dbCon->lastInsertId(), 'message' => 'Thêm tin nhắn thành công')));
    } catch (PDOException $ex) {
        die(json_encode(array('status' => false, 'message' => $ex->getMessage())));
    }
