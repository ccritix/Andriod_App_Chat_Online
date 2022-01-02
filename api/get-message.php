<?php

    require_once ('connection.php');

    if (!isset($_POST['user1']) || !isset($_POST['user2'])) {
        die(json_encode(array('status' => false, 'message' => 'Parameters not valid')));
    }

    $user1 = $_POST['user1'];
    $user2 = $_POST['user2'];

    $sql = 'SELECT * FROM '.$user1.' where user1 = ? and user2 = ?';

    try{
        $stmt = $dbCon->prepare($sql);
        $stmt->execute(array($user1, $user2));
    }
    catch(PDOException $ex){
        die(json_encode(array('status' => false, 'message' => $ex->getMessage())));
    }

    $data = array();
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC))

    {
        $data[] = $row;
    }

    echo json_encode(array('status' => true, 'data' => $data, 'message' => 'Load success'));
