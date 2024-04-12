FROM python:3.9
# Or any preferred Python version.
ADD env/main.py .
RUN pip install requests beautifulsoup4 python-dotenv
CMD ["python", "./main.py"]


