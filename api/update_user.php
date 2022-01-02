<?php
    require_once ('connection.php');

    if (!isset($_POST['username']) || !isset($_POST['oldpass']) || !isset($_POST['newpass'])) {
        die(json_encode(array('status' => false, 'message' => 'Parameters not valid')));
    }

    $username = $_POST['username'];
    $oldpass = $_POST['oldpass'];
    $newpass = $_POST['newpass'];

    $sql = 'UPDATE user set password = ? where username = ? and password = ?';

    try{
        $stmt = $dbCon->prepare($sql);
        $stmt->execute(array($newpass, $username, $oldpass));

        $count = $stmt->rowCount();

        if ($count == 1) {
            die(json_encode(array('status' => true, 'message' => 'Update Success')));
        }else {
            die(json_encode(array('status' => false, 'message' => 'Update Fail')));
        }


    }
    catch(PDOException $ex){
        die(json_encode(array('status' => false, 'message' => $ex->getMessage())));
    }
