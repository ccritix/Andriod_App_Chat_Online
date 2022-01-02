<?php
    
    require_once ('connection.php');

    $sql = 'SELECT * FROM user';

    try{
        $stmt = $dbCon->prepare($sql);
        $stmt->execute();
    }
    catch(PDOException $ex){
        die(json_encode(array('status' => false, 'message' => $ex->getMessage())));
    }

    $data = array();
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC))

    {
        $data[] = $row;
    }

    die(json_encode(array('status' => true, 'data' => $data, 'message' => 'Load success')));
