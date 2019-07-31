FROM jenkins/jenkins:2.176.2

USER root
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add -

RUN apt-get update && \
    apt-get install -y  apt-transport-https

RUN echo 'deb [arch=amd64] https://download.docker.com/linux/debian stretch stable' >> /etc/apt/sources.list && \
    apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install docker-ce-cli 
USER jenkins

RUN /usr/local/bin/install-plugins.sh workflow-aggregator git
RUN echo 2.0 > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state
ADD scriptApproval.xml /usr/share/jenkins/ref/
ADD jobs /usr/share/jenkins/ref/jobs
ADD secrets /usr/share/jenkins/ref/secrets
