//
//  ViewController.swift
//  SpringRestConsumer
//
//  Created by Merter Sualp on 8/24/16.
//  Copyright © 2016 Merter Sualp. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var id: UILabel!
    @IBOutlet weak var content: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func getGreeting(_ sender: AnyObject) {
        let url = URL(string: "http://localhost:8080/greeting")
        let session = URLSession.shared
        let dataTask = session.dataTask(with: url!, completionHandler: { data, response, error in
            if let error = error {
                print("Failure! \(error)")
            } else if let httpResponse = response as? HTTPURLResponse, httpResponse.statusCode == 200 {
                print("Success! \(data!)")
                let greeting = self.parseJSON(data: data!)
                let idNum = greeting?["id"] as? Int
                self.id.text = "\(idNum!)"
                self.content.text = greeting?["content"] as? String
            } else {
                print("Failure! \(response!)")
            }
        })
        dataTask.resume()
    }
    
    func parseJSON(data: Data) -> [String: AnyObject]? {
        do {
            return try JSONSerialization.jsonObject(with: data as Data, options: []) as? [String: AnyObject]
        } catch {
            print("JSON Error: \(error)")
            return nil
        }
    }
}

