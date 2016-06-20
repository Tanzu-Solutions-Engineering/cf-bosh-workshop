# BOSH One Day Workshop

This is a One day training on BOSH that will allow ops teams to get familiar with bosh concepts and also start using bosh in their environments.

## Install PCF DEV

1. Download the latest `pcfdev-VERSION-PLATFORM.zip` from the [Pivotal Network](https://network.pivotal.io/).
1. Unzip the zip file and navigate to its containing folder.
1. Run `cf install-plugin pcfdev-VERSION-PLATFORM` at your command line
1. Run `cf dev start`

> Check out the [documentation](https://docs.pivotal.io/pcf-dev/) for more information. Running `cf dev help` will display an overview of PCF Dev VM management commands.

### Prerequisites

* [CF CLI](https://github.com/cloudfoundry/cli)
* [VirtualBox](https://www.virtualbox.org/): 5.0+
* Internet connection (or [Dnsmasq](http://www.thekelleys.org.uk/dnsmasq/doc.html) or [Acrylic](http://mayakron.altervista.org/wikibase/show.php?id=AcrylicHome)) required for wildcard DNS resolution

### Using the Cloud Foundry CLI Plugin

Follow the instructions provided at the end of `cf dev start` to connect to PCF Dev:

```
 _______  _______  _______    ______   _______  __   __
|       ||       ||       |  |      | |       ||  | |  |
|    _  ||       ||    ___|  |  _    ||    ___||  |_|  |
|   |_| ||       ||   |___   | | |   ||   |___ |       |
|    ___||      _||    ___|  | |_|   ||    ___||       |
|   |    |     |_ |   |      |       ||   |___  |     |
|___|    |_______||___|      |______| |_______|  |___|
is now running.
To begin using PCF Dev, please run:
	cf login -a api.local.pcfdev.io --skip-ssl-validation
Email: admin
Password: admin
```

## Install PROCTOR
A tool for running BOSH 101 classrooms.

- [Binary releases](https://github.com/rosenhouse/proctor/releases)

- [Pivotal Tracker project](https://www.pivotaltracker.com/n/projects/1434846)


### Basic usage
0. Load credentials for your AWS environment
    ```
    export AWS_DEFAULT_REGION=us-east-1  # this can be any region that has a BOSH-lite AMI
    export AWS_ACCESS_KEY_ID=YOUR-ACCESS-KEY
    export AWS_SECRET_ACCESS_KEY=YOUR-SECRET-KEY
    ```

0. Create a new classroom
    ```
    proctor create -name my-classroom -number 3
    ```
    This will spin up 3 EC2 instances in your AWS account.

0. Watch your classroom get created
    ```
    proctor describe -name my-classroom
    ```
    The SSH key was generated at `create` time and is world-readable.

0. Run a command on all VMs
    ```
    proctor run -name my-classroom -c 'bosh status'
    ```

0. Destroy your classroom
    ```
    proctor destroy -name my-classroom
    ```

# Copyright

See [LICENSE](LICENSE) for details.
Copyright (c) 2016 [Pivotal Software, Inc](http://www.pivotal.io/).

PCF Dev uses a version of Monit that can be found [here](https://github.com/pivotal-cf/pcfdev-monit), under the GPLv3 license.
